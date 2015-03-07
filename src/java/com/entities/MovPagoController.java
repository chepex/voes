package com.entities;

import com.entities.util.JsfUtil;
import com.entities.util.JsfUtil.PersistAction;

import java.io.Serializable;
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

@ManagedBean(name = "movPagoController")
@SessionScoped
public class MovPagoController implements Serializable {

    @EJB
    private com.entities.MovPagoFacade ejbFacade;
    private List<MovPago> items = null;
    private MovPago selected;

    public MovPagoController() {
    }

    public MovPago getSelected() {
        return selected;
    }

    public void setSelected(MovPago selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setMovPagoPK(new com.entities.MovPagoPK());
    }

    private MovPagoFacade getFacade() {
        return ejbFacade;
    }

    public MovPago prepareCreate() {
        selected = new MovPago();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MovPagoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MovPagoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MovPagoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<MovPago> getItems() {
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

    public List<MovPago> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<MovPago> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = MovPago.class)
    public static class MovPagoControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MovPagoController controller = (MovPagoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "movPagoController");
            return controller.getFacade().find(getKey(value));
        }

        com.entities.MovPagoPK getKey(String value) {
            com.entities.MovPagoPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.entities.MovPagoPK();
            key.setCodTipoPago(Integer.parseInt(values[0]));
            key.setCodMov(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.entities.MovPagoPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getCodTipoPago());
            sb.append(SEPARATOR);
            sb.append(value.getCodMov());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof MovPago) {
                MovPago o = (MovPago) object;
                return getStringKey(o.getMovPagoPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), MovPago.class.getName()});
                return null;
            }
        }

    }

}
