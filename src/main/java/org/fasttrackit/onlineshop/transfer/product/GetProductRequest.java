package org.fasttrackit.onlineshop.transfer.product;

public class GetProductRequest {

    private String partialName;
//    am pus Integer ca si clasa si nu "int" ca si proprietate pentru a suporta valoarea null
//    se mai numeste si Wrapper classes cand proprietatile au prima litera majuscula
    private Integer minimumQuantity;

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    public Integer getMinimumQuantity() {
        Integer minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }


    @Override
    public String toString() {
        return "GetProductRequests{" +
                "partialName='" + partialName + '\'' +
                ", minimumQuantity=" + minimumQuantity +
                '}';
    }
}
