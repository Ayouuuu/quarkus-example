package com.ayou;

import com.ayou.exception.MemberExistException;
import com.ayou.exception.PartyNotOwnerException;
import com.ayou.exception.PartyNotPublicException;
import com.ayou.pojo.Party;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.gradle.internal.impldep.com.google.common.collect.Lists;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SmallryeTest {
    private static Logger log = Logger.getLogger(SmallryeTest.class);

    /**
     * call 异步执行方法
     */
    @Test
    public void testAsyncCall() {
        Uni.createFrom().item(new ListTest())
                .call(p -> Multi.createFrom().items(p.list.stream()).call(this::call).collect().asList())
                .call(p -> this.call(p.id)).await().indefinitely();
    }


    public Uni<Void> call(Object o) {
        log.info(o);
        return Uni.createFrom().voidItem();
    }

    public static class ListTest {
        private Integer id = 1;
        private List<String> list = Lists.newArrayList("a", "b", "c");
    }
}
