package tag_lib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by Oleg Grigorjev 
 */
 
public class DateToAge extends TagSupport {

    private LocalDate value;

    public void setValue(LocalDate dob) {
        this.value = dob;
    }

    @Override
    public int doStartTag() throws JspException {
        long age = ChronoUnit.YEARS.between(value, LocalDate.now());
        try {
            pageContext.getOut().write("<p align=\"right\">" + age + "</p>");

        }catch(IOException e){
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
