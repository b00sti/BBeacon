package com.example.b00sti.bbeacon.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dominik (b00sti) Pawlik on 2017-03-09
 */

@Getter
@Setter
@AllArgsConstructor
public class Beacon extends RealmObject {

    @PrimaryKey
    private String id;

    private String name;

    public Beacon() {
    }
}
