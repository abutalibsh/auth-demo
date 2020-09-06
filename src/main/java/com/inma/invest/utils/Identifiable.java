package com.inma.invest.utils;

import java.io.Serializable;

/**
 * Identifiable - Identifiable
 *
 * @author salah atwa
 */
public interface Identifiable<T extends Serializable> {

    T getId();
}