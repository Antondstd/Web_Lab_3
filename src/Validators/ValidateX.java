package Validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.net.URI;
import java.net.URISyntaxException;

@FacesValidator("ValidateX")
public class ValidateX implements Validator {

    @Override
    public void validate(FacesContext facesContext,
                         UIComponent component, Object value)
            throws ValidatorException {
        String strValue = value.toString();
        Double xValue;
        try{
            xValue = Double.parseDouble(strValue);
        }
        catch (Exception e)
        {
            xValue = null;
            FacesMessage msg = new FacesMessage("Некорректное значение","Введеное значение X не является числом");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        if (xValue != null){
            if (xValue<-3 || xValue > 5){
                FacesMessage msg = new FacesMessage("Выход из ОДЗ","Введеное значение X не входит в ОДЗ [-3;5]");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }
}