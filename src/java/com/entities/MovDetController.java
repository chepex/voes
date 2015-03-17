package com.entities;

import com.entities.util.JsfUtil;
import com.entities.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
    
    @EJB
    private com.entities.TipoPagoFacade tipoPagoFacade;     
    private List<MovDet> items = null;
    
    
    private MovDet selected;
    private List<MovDet> lmovdet =  new ArrayList<MovDet>();
    private List<MovPago> lmovpago =  new ArrayList<MovPago>();
    private int vcor;
    private int codigo;
    private BigDecimal vtotal;
    private TipoPago vtipoPago;
    private BigDecimal vpendiente;
    private BigDecimal vcambio;
    
    
    
    public MovDetController() {
    }

    public int getVcor() {
        return vcor;
    }

    public void setVcor(int vcor) {
        this.vcor = vcor;
    }

    public BigDecimal getVtotal() {
        return vtotal;
    }

    public void setVtotal(BigDecimal vtotal) {
        this.vtotal = vtotal;
    }

    public BigDecimal getVpendiente() {
        return vpendiente;
    }

    public void setVpendiente(BigDecimal vpendiente) {
        this.vpendiente = vpendiente;
    }

    public BigDecimal getVcambio() {
        return vcambio;
    }

    public void setVcambio(BigDecimal vcambio) {
        this.vcambio = vcambio;
    }

    
    
    public List<MovPago> getLmovpago() {
        return lmovpago;
    }

    public void setLmovpago(List<MovPago> lmovpago) {
        this.lmovpago = lmovpago;
    }

    

    
    public TipoPago getVtipoPago() {
        return vtipoPago;
    }

    public void setVtipoPago(TipoPago vtipoPago) {
        this.vtipoPago = vtipoPago;
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
                if(lmovpago.isEmpty() ){
                    List <TipoPago> ltpago = tipoPagoFacade.findAll();                    
                    for(TipoPago tp: ltpago ){
                        System.out.println("pagos"+tp);
                        System.out.println("lmovpago"+lmovpago);
                        MovPago  mp= new MovPago(tp.getCodTipoPago(),0);
                        mp.setTipoPago(tp);
                        mp.setValor(BigDecimal.ZERO);
                        lmovpago.add(mp);
                    }
                }
        }
        vcor++;
        selected.getMovDetPK().setCorrelativo(vcor);
    }

    private MovDetFacade getFacade() {
        return ejbFacade;
    }

    public MovDet prepareCreate() {
           
        
        selected = new MovDet();
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
        double a =  selected.getCantidad().doubleValue() * selected.getCodProd().getPrecio().doubleValue();
        if(lmovdet==null){
                              
                selected.setTotal( new BigDecimal(a));
        }else{
            selected.setTotal(new BigDecimal(a));
            lmovdet.add(selected);
        }
        if(vtotal==null){
            vtotal =  new BigDecimal(a);
        }else{
            vtotal = vtotal.add(new BigDecimal(a));
        }
        
        this.prepareCreate();
        codigo=0;            
        return "";
    }
    

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MovDetUpdated"));
    }

    public void pagar() {
        
        
        limpiar();
        
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
        vcambio = BigDecimal.ZERO;
       //this.vpendiente = this.vtotal - this.vcheque - this.vefectivo -this.vpuntos - this.vtransferencia -this.vcredito;
        BigDecimal att =BigDecimal.ZERO;
        
        for(MovPago mp :lmovpago){
            if(mp.getValor().compareTo(BigDecimal.ZERO)==1){
            att = att.add( mp.getValor());
            }        
        }
        
        /*si pago es mayor q total cambio >0*/
       
        if(att.compareTo(vtotal)==1){
            vcambio =    this.vtotal.subtract(att);
            vpendiente =BigDecimal.ZERO;
        }else{
            vpendiente = this.vtotal.subtract(att);
        }
        if(vpendiente.compareTo(BigDecimal.ZERO)==-1){
            vpendiente= BigDecimal.ZERO;
        }
        
        
        
        
        
        return "";
    }
    
    public String limpiar(){
        lmovdet =    new ArrayList<MovDet>();
        lmovpago=    new ArrayList<MovPago>();
        this.vpendiente=BigDecimal.ZERO;
     
        this.vcambio=  BigDecimal.ZERO;
        this.vpendiente= BigDecimal.ZERO;
        this.vtotal= BigDecimal.ZERO;        
        return "";
    }
    
    public String cancelar(){
        limpiar();
        this.prepareCreate();
        
        
        return "";
    }
    
}
            