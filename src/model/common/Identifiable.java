package model.common;

import java.io.Serializable;

/**
 * Interface commune pour tous les objets métier possédant un identifiant.
 */
public interface Identifiable extends Serializable {
    String getId();
}