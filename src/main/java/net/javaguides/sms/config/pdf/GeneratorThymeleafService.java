package net.javaguides.sms.config.pdf;

import com.lowagie.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class GeneratorThymeleafService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ServletContext servletContext;

    private Logger logger = LoggerFactory.getLogger(GeneratorThymeleafService.class);

    //@Value("${deploy-host}")
    private String urlBase="/";

    public ByteArrayOutputStream createPdf(String templateName,
                                           Map<String, Object> userPayload,
                                           HttpServletRequest request,
                                           HttpServletResponse response)
            throws DocumentException {
        IWebContext ctx = new WebContext(request, response, servletContext, LocaleContextHolder.getLocale(),
                userPayload);
        try {
            String processedHtml = templateEngine.process(templateName, ctx);
            var bos = new ByteArrayOutputStream();
            var renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml, urlBase);
            renderer.layout();
            renderer.createPDF(bos, false);
            renderer.finishPDF();
            bos.close();
            return bos;
        } catch (IOException e) {
            logger.error("Error creando pdf", e);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
