package ru.itmo.lab1.common;

import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

@Component
public class HtmlEscaper {

    public String escape(String value) {
        return HtmlUtils.htmlEscape(value);
    }
}
