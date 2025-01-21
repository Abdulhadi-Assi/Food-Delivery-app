package food_delivery.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


@Entity
@Table(name = "user_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)  // Don't modify the foreign key directly
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id", insertable = false, updatable = false)  // Don't modify the foreign key directly
    private Role role;

}