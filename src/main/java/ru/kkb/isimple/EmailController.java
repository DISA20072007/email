package ru.kkb.isimple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kkb.isimple.dao.BranchDao;
import ru.kkb.isimple.dao.EmailCategoryDao;
import ru.kkb.isimple.dao.EmailDao;
import ru.kkb.isimple.dao.EmailTopicDao;
import ru.kkb.isimple.entities.Branch;
import ru.kkb.isimple.entities.Email;
import ru.kkb.isimple.entities.EmailCategory;
import ru.kkb.isimple.entities.EmailTopic;
import ru.kkb.isimple.jmx.ExceptionMBean;

import java.util.List;
import java.util.Map;

/**
 * @author denis.fedorov
 */

@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailTopicDao emailTopicDao;

    @Autowired
    private BranchDao branchDao;

    @Autowired
    private EmailCategoryDao emailCategoryDao;

    @Autowired
    private EmailDao emailDao;

    @Autowired
    private ExceptionMBean exceptionMBean;

    private final static String HOME_PAGE = "email";
    private final static String INTERNAL_SERVER_ERROR = "Внутренняя ошибка сервера";

    @RequestMapping("")
    public String getAll(Map<String, Object> model) {
        model.put("topics", getEmailTopics());
        model.put("emails", getEmails());

        return HOME_PAGE;
    }

    @RequestMapping("/actions/setTopic")
    @ResponseBody
    public Response setTopic(@RequestParam(name = "id") int id,
                             @RequestParam(name = "name") String name) {

        try {
          /*  if (StringUtils.isEmpty(name)) {
                return new Response("Наименование темы не может быть пустой");
            } */

            emailTopicDao.setTopic(id, name);
        } catch (Exception ex) {
            exceptionMBean.addException(ex);
            return new Response(ex.getMessage());
        }

        return Response.RESPONSE_OK;
    }

    @RequestMapping("/actions/deleteTopic")
    @ResponseBody
    public Response deleteTopic(@RequestParam(name = "id") int id) {

        try {
            if (emailDao.isExist(id)) {
                return new Response("Данная тема используется в справочнике получателей писем");
            }

            emailTopicDao.deleteTopic(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            exceptionMBean.addException(ex);
            return new Response(INTERNAL_SERVER_ERROR);
        }

        return Response.RESPONSE_OK;
    }

    @RequestMapping("/actions/setEmail")
    @ResponseBody
    public Response setEmail(@RequestParam(name = "topicId") int topicId,
                             @RequestParam(name = "branchId") int branchId,
                             @RequestParam(name = "categoryId") int categoryId,
                             @RequestParam(name = "email") String email) {

        try {
            if (StringUtils.isEmpty(email)) {
                return new Response("Адрес электронной почты не может быть пустой");
            }

            emailDao.setEmail(topicId, branchId, categoryId, email);
        } catch (Exception ex) {
            exceptionMBean.addException(ex);
            return new Response(ex.getMessage());
        }

        return Response.RESPONSE_OK;
    }

    @RequestMapping("/actions/deleteEmail")
    public String deleteEmail(@RequestParam(name = "topicId") int topicId,
                              @RequestParam(name = "branchId") int branchId,
                              @RequestParam(name = "categoryId") int categoryId) {
        try {
            emailDao.deleteEmail(topicId, branchId, categoryId);
        } catch (Exception ex) {
            exceptionMBean.addException(ex);
        }

        return HOME_PAGE;
    }

    @RequestMapping(value = "/getTopics", method = RequestMethod.GET)
    @ResponseBody
    public List<EmailTopic> getTopics() {
        return getEmailTopics();
    }

    @RequestMapping(value = "/getBranches", method = RequestMethod.GET)
    @ResponseBody
    public List<Branch> getBranches() {
        return getBranchList();
    }

    @RequestMapping(value = "/getCategories", method = RequestMethod.GET)
    @ResponseBody
    public List<EmailCategory> getCategories() {
        return getEmailCategories();
    }

    private List<EmailTopic> getEmailTopics() {
        return emailTopicDao.getMailTopics();
    }

    private List<Branch> getBranchList() {
        return branchDao.getBranches();
    }

    private List<EmailCategory> getEmailCategories() {
        return emailCategoryDao.getCategories();
    }

    private List<Email> getEmails() {
       return emailDao.getEmails();
    }
}
