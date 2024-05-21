package com.example.servicoderemessauser.fixture;

import lombok.Getter;

@Getter
public enum BaseFixture {

    MODEL("package com.example.servicoderemessauser.fixture.model"),
    ALL("com.example.servicoderemessauser.fixture");

    private final String pacote;

    BaseFixture(final String pacote) {
        this.pacote = pacote;
    }
}
