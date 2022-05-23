package tags;

import models.daos.AdminDAO;
import models.entities.Auth;
import models.entities.Horse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.List;

public class TableTag extends BodyTagSupport {
    private static final Logger logger = LogManager.getLogger(TableTag.class);

    private List<Horse> items;
    private int itemPos;
    private boolean isAdmin;

    public void setItems(List<Horse> items) {
        this.items = items;
        itemPos = 0;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            Auth user = (Auth) pageContext.getSession().getAttribute("user");
            isAdmin = user.getUserType().equals("admin");

            logger.info(user);
            logger.info("Is admin? " + isAdmin);

            pageContext.getOut().write("<table class=\"table horses-table\">\n" +
                    "        <thead>\n" +
                    "        <tr class=\"horses-table__header\">\n" +
                    "            <th class=\"horses-table__col\" scope=\"col\">ID</th>\n" +
                    "            <th class=\"horses-table__col\" scope=\"col\">Name</th>\n" +
                    "            <th class=\"horses-table__col\" scope=\"col\">Rider name</th>\n" +
                    (isAdmin ? "            <th class=\"horses-table__col\" scope=\"col\">Edit</th>\n" : "") +
                    "        </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>" +
                    "            <tr class=\"horses-table__row\">\n" +
                    "                <td class=\"horses-table__col\" scope=\"row\">");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspException {
        if (itemPos < items.size()) {
            Horse horse = items.get(itemPos);
            itemPos++;
            try {
                pageContext.getOut().write(horse.getId() + "</td>\n" +
                        "                <td class=\"horses-table__col\">" + horse.getName() + "</td>\n" +
                        "                <td class=\"horses-table__col\">" + horse.getRiderName() + "</td>\n" +
                        (isAdmin ? "     <td class=\"horses-table__col\">" +
                                "<form method=\"POST\">\n" +
                                "   <input type=\"text\" name=\"horseName\">\n" +
                                "   <input type=\"hidden\" name=\"horseId\" value=\"" + horse.getId() + "\">\n" +
                                "   <button type=\"submit\" class=\"btn btn-primary\">Save</button>\n" +
                                "</form>" +
                        "   </td>\n" : "") +
                        "</tr>" +
                        "<tr class=\"horses-table__row\">\n" +
                        "   <td class=\"horses-table__col\" scope=\"row\">");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }


    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().write("</td></tr></tbody></table>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SKIP_BODY;
    }
}
