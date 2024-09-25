package net.template.server.language.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LanguageDTO {
    private long id;

    private Integer version;

    private String code;

    private String name;

}
