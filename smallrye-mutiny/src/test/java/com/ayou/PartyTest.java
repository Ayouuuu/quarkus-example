package com.ayou;

import com.ayou.exception.MemberExistException;
import com.ayou.exception.PartyNotFoundException;
import com.ayou.exception.PartyNotOwnerException;
import com.ayou.exception.PartyNotPublicException;
import com.ayou.pojo.Party;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import lombok.extern.jbosslog.JBossLog;
import org.gradle.internal.impldep.com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@JBossLog
public class PartyTest {

    private Party party;

    @BeforeEach
    public void before(){
        party = Party.newBuilder().owner("Ayou").member(Lists.newArrayList("Player", "Player2")).build();
    }

    @Test
    public void disbandTest(){
        // 找不到队伍
        Party.getParty("null").onFailure().invoke(log::info).subscribe().withSubscriber(UniAssertSubscriber.create()).assertFailedWith(PartyNotFoundException.class);
        // 不是队长
        party.disband("Player").onFailure().invoke(log::info).subscribe().withSubscriber(UniAssertSubscriber.create()).assertFailedWith(PartyNotOwnerException.class);
    }

    @Test
    public void inviteTest(){
        party.invite("Player").onFailure().invoke(log::info)
                .subscribe().withSubscriber(UniAssertSubscriber.create()).assertFailedWith(MemberExistException.class);
        party.invite("PlayerC").invoke(log::info)
                .subscribe().withSubscriber(UniAssertSubscriber.create()).assertCompleted();
    }

    @Test
    public void publicJoin(){
        party.setPublic_(true);
        // 成功加入队伍
        assertTrue(Party.publicJoin(party, "Player4").await().indefinitely());
        log.info(party);
        // 关闭公共加入
        party.setPublic_(false);
        Party.publicJoin(party,"Player3").onFailure().invoke(log::info).subscribe().withSubscriber(UniAssertSubscriber.create()).assertFailedWith(PartyNotPublicException.class);
    }

    @Test
    public void multiUniTest(){
        Party.getParty("Player").invoke(log::info)
                .flatMap(p -> p.disband("Player"))
                .subscribe().withSubscriber(UniAssertSubscriber.create()).assertFailedWith(PartyNotOwnerException.class).awaitFailure(log::info);
    }

}
