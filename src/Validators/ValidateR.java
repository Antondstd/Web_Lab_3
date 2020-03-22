package Validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.net.URI;
import java.net.URISyntaxException;

@FacesValidator("ValidateR")
public class ValidateR implements Validator {

    @Override
    public void validate(FacesContext facesContext,
                         UIComponent component, Object value)
            throws ValidatorException {
        Double xValue;
        try{
            String strValue = value.toString();
            xValue = Double.parseDouble(strValue);
        }
        catch (Exception e)
        {
            xValue = null;
            FacesMessage msg =
                    new FacesMessage("Значение R должно быть числом");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        if (xValue != null){
            if (xValue < 2 || xValue > 5){
                FacesMessage msg = new FacesMessage("Выход из ОДЗ","Введеное значение R не входит в ОДЗ [2;5]");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }
}
