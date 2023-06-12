package pl.lodz.p.it.ssbd2023.ssbd03.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2023.ssbd03.dto.VersionDTO;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyPlaceDTO extends VersionDTO implements Serializable {

    @DecimalMin(value = "0")
    @Digits(integer = 8, fraction = 2, message = "value can have 8 digits before the decimal point and max 2 digits after the decimal point")
    private BigDecimal area;

    public ModifyPlaceDTO(@NotNull Long version, BigDecimal area, Boolean hotWaterConnection, Boolean centralHeatingConnection) {
        super(version);
        this.area = area;
    }
}
