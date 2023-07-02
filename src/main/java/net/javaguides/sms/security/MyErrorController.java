package net.javaguides.sms.security;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping(value = "/error", method = RequestMethod.GET) //NOSONAR
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            var statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "views/errorpages/error403";
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "views/errorpages/error404";
            } else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                return "views/errorpages/error405";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "views/errorpages/error500";
            }
        }
        return "redirect:/dashboard";
    }
}