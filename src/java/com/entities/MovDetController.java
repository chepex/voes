package com.entities;

import com.entities.util.JsfUtil;
import com.entities.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "movDetController")
@SessionScoped
public class MovDetController implements Serializable {

    @EJB
    private com.entities.MovDetFacade ejbFacade;

    @EJB
    private com.entities.ProductoFacade productFacade;    
    private List<MovDet> items = null;
    private MovDet selected;
    private List<MovDet> lmovdet =  new ArrayList<MovDet>();
    private int vcor;
    private int codigo;
    private Long vtotal;
    private TipoPago vtipoPago;
    private Long Vvalor;
    private Long Vreferencia;
    private Long vefectivo;
    private Long vcredito;

    private Long vcheque;
    private Long vales;
    private Long vtransferencia  ;
    private Long vpendiente;
    private Long vcambio;
    
    
    
    public MovDetController() {
    }

    public Long getVtransferencia() {
        return vtransferencia;
    }

    public void setVtransferencia(Long vtransferencia) {
        this.vtransferencia = vtransferencia;
    }

    public Long getVpendiente() {
        return vpendiente;
    }

    public void setVpendiente(Long vpendiente) {
        this.vpendiente = vpendiente;
    }

    public Long getVcambio() {
        return vcambio;
    }

    public void setVcambio(Long vcambio) {
        this.vcambio = vcambio;
    }


    
    


    
    public Long getVefectivo() {
        return vefectivo;
    }

    public Long getVales() {
        return vales;
    }

    public void setVales(Long vales) {
        this.vales = vales;
    }





    
    public void setVefectivo(Long vefectivo) {
        this.vefectivo = vefectivo;
    }

    public Long getVcredito() {
        return vcredito;
    }

    public void setVcredito(Long vcredito) {
        this.vcredito = vcredito;
    }

   

    public Long getVcheque() {
        return vcheque;
    }

    public void setVcheque(Long vcheque) {
        this.vcheque = vcheque;
    }

    
    public TipoPago getVtipoPago() {
        return vtipoPago;
    }

    public void setVtipoPago(TipoPago vtipoPago) {
        this.vtipoPago = vtipoPago;
    }

    public Long getVvalor() {
        return Vvalor;
    }

    public void setVvalor(Long Vvalor) {
        this.Vvalor = Vvalor;
    }

    public Long getVreferencia() {
        return Vreferencia;
    }

    public void setVreferencia(Long Vreferencia) {
        this.Vreferencia = Vreferencia;
    }

    
    
    
    public Long getVtotal() {
        return vtotal;
    }

    public void setVtotal(Long vtotal) {
        this.vtotal = vtotal;
    }

    

    
    
    public MovDet getSelected() {
        return selected;
    }

    public void setSelected(MovDet selected) {
        this.selected = selected;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public List<MovDet> getLmovdet() {
        return lmovdet;
    }

    public void setLmovdet(List<MovDet> lmovdet) {
        this.lmovdet = lmovdet;
    }

    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setMovDetPK(new com.entities.MovDetPK());
        if(lmovdet.isEmpty()){
            vcor = 0;
            
        }
        vcor++;
        selected.getMovDetPK().setCorrelativo(vcor);
    }

    private MovDetFacade getFacade() {
        return ejbFacade;
    }

    public MovDet prepareCreate() {
        selected = new MovDet();
        this.vpendiente=Long.valueOf(0);
            this.vtransferencia = Long.valueOf(0);
            this.vcheque=Long.valueOf(0);
            this.vcredito=Long.valueOf(0);
            this.vefectivo=Long.valueOf(0);
            this.vales=Long.valueOf(0);
            
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MovDetCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public String add() {
        
        if(codigo ==0){
            JsfUtil.addErrorMessage("Selecione un producto");
            return "";
        }
        Producto p= productFacade.find(codigo);
        
        if(p==null){
            JsfUtil.addErrorMessage("Producto no existe");
            return "";
        }
        
        selected.setCodProd(p);
        if(selected.getCantidad()==null){
            selected.setCantidad(1);
        }
        selected.setPrecio(p.getPrecio());
        Long a =  selected.getCantidad().longValue() * selected.getCodProd().getPrecio().intValue();
        if(lmovdet==null){
                              
                selected.setTotal(a);
        }else{
            selected.setTotal(a);
            lmovdet.add(selected);
        }
        if(vtotal==null){
            vtotal =  a;
        }else{
            vtotal = vtotal + a;
        }
        
        this.prepareCreate();
        codigo=0;            
        return "";
    }
    

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MovDetUpdated"));
    }

    public void pagar() {
         JsfUtil.addErrorMessage("Pago realizado correctamente");
    }
       
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MovDetDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<MovDet> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<MovDet> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<MovDet> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = MovDet.class)
    public static class MovDetControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MovDetController controller = (MovDetController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "movDetController");
            return controller.getFacade().find(getKey(value));
        }

        com.entities.MovDetPK getKey(String value) {
            com.entities.MovDetPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.entities.MovDetPK();
            key.setCodMov(Integer.parseInt(values[0]));
            key.setCorrelativo(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.entities.MovDetPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getCodMov());
            sb.append(SEPARATOR);
            sb.append(value.getCorrelativo());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof MovDet) {
                MovDet o = (MovDet) object;
                return getStringKey(o.getMovDetPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), MovDet.class.getName()});
                return null;
            }
        }

    }

    public String actualizarTotal()
    {
        vcambio = (long)0;
       //this.vpendiente = this.vtotal - this.vcheque - this.vefectivo -this.vpuntos - this.vtransferencia -this.vcredito;
        Long att =   this.vefectivo + this.vcheque + this.vales + this.vtransferencia + this.vcredito;
        
        
        /*si pago es mayor q total cambio >0*/
        System.out.println("----->vefectivo"+vefectivo);
        System.out.println("----->vcheque"+vcheque);
        System.out.println("----->vales"+vales);
        System.out.println("----->vtransferencia"+vtransferencia);
        System.out.println("----->vcredito"+vcredito);
        System.out.println("----->att"+att);
        System.out.println("----->total"+vtotal);
        if(att > vtotal){
            vcambio =    this.vtotal -  att;
        }else{
            vpendiente = this.vtotal -  att;
        }
        if(vpendiente <0){
            vpendiente= Long.valueOf(0);
        }
        
        
        
        
        
        return "";
    }
    
    public String limpiar(){
        lmovdet =    new ArrayList<MovDet>();
        this.vcambio=  Long.valueOf(0);
        this.vpendiente= Long.valueOf(0);
        this.vtotal= Long.valueOf(0);
        this.prepareCreate();
        return "";
    }
}
            