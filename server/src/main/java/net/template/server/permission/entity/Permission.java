package net.template.server.permission.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "SYS_PERMISSIONS")
@Table(name = "SYS_PERMISSIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @SequenceGenerator(name = "sys_permissions_sequence", sequenceName = "sys_permissions_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sys_permissions_sequence")
    @Column(name = "ID")
    private long id;

    @Column(name = "CODE", unique = true)
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

}
