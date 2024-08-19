package com.example.ecom.customException;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Entity not found");
    }

    public NotFoundException(char entity) {
        super(getMessageForEntity(entity));
    }

    private static String getMessageForEntity(char entity) {
        switch (Character.toUpperCase(entity)) {
            case 'P':
                return "Product not found";
            case 'O':
                return "Order not found";
            case 'U':
                return "User not found";
            case 'Z':
                return "User doesn't has any products";
            default:
                return "Entity not found";
        }
    }
}
