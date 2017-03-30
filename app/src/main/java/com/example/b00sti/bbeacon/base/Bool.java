package com.example.b00sti.bbeacon.base;

import io.realm.RealmObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-30
 */


@Getter
@Setter
@AllArgsConstructor
public class Bool extends RealmObject {
    boolean isEnabled;

    public Bool() {
    }
}
