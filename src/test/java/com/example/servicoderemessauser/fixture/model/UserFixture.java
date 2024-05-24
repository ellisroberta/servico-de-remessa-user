package com.example.servicoderemessauser.fixture.model;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.example.servicoderemessauser.enums.UserTypeEnum;
import com.example.servicoderemessauser.model.User;
import org.apache.commons.lang.RandomStringUtils;

import java.util.UUID;

public class UserFixture implements TemplateLoader {

    public static final String VALIDO = "valido";

    @Override
    public void load() {
        templateValido();
    }

    private void templateValido() {
        Fixture.of(User.class).addTemplate(VALIDO, new Rule() {
            {
                add("id", UUID.randomUUID());
                add("fullName", RandomStringUtils.randomAlphabetic(10));
                add("email", RandomStringUtils.randomAlphabetic(5) + "@example.com");
                add("password", RandomStringUtils.randomAlphanumeric(10));
                add("document", RandomStringUtils.randomNumeric(11)); // ou RandomStringUtils.randomNumeric(14)
                add("userType", UserTypeEnum.PF); // ou UserTypeEnum.PJ
            }
        });
    }
}
