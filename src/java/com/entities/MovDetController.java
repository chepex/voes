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
    private List<MovDet> items = null;
    private MovDet selected;
    private List<MovDet> lmovdet =  new ArrayList<MovDet>();
    private int vcor;
    
    public MovDetController() {
    }

    public MovDet getSelected() {
        return selected;
    }

    public void setSelected(MovDet selected) {
        this.selected = selected;
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
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MovDetCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void add() {
        System.out.println("lmovdet ---- >"+ lmovdet);
        Integer a =  selected.getCantidad() * selected.getCodProd().getPrecio();
    if(lmovdet==null){
            lmovdet = null;
            
    }else{
        selected.setTotal(a.longValue());
        lmovdet.add(selected);
    }
        this.prepareCreate();
        System.out.println("lmovdet ---- >"+ lmovdet);
    }
    

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MovDetUpdated"));
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

}
