package com.example.product.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * Se utiliza para más seguridad.
 * No se envía el objeto en sí guardado en BBDD sino la "réplica" de este con esta clase.
 */
public class ProductDTO {

    private String id;
    private String name;
    private String brand;
    private Integer units;
    private Double price;
    private String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductDTO that = (ProductDTO) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
