package net.template.server.language.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity(name = "SYS_LANGUAGES")
@Table(name = "SYS_LANGUAGES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Language {

    @Id
    @SequenceGenerator(name = "sys_languages_sequence", sequenceName = "sys_languages_sequence", allocationSize = 1)
    @GeneratedValue(generator = "sys_languages_sequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private long id;

    @Version
    @Column(name = "OPTLOCK")
    private Integer version;

    @Column(name = "CODE", length = 12, unique = true, nullable = false)
    private String code;

    @Column(name = "NAME", length = 24)
    private String name;

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Language language = (Language) object;
        return Objects.equals(getId(), language.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
