package com.ayou.pojo;

import com.ayou.exception.MemberExistException;
import com.ayou.exception.PartyNotFoundException;
import com.ayou.exception.PartyNotOwnerException;
import com.ayou.exception.PartyNotPublicException;
import io.smallrye.mutiny.Uni;
import lombok.Data;

import java.util.HashMap;
import java.util.List;


/**
 * 这是一个关于组队系统的响应式实现，采用了抛出异常，下游捕捉异常做出相应反馈
 */
@Data
public class Party {
    public static final HashMap<String,Party> partys = new HashMap<>();
    private String owner;
    private List<String> member;

    public boolean public_;

    private Party(Builder builder) {
        setOwner(builder.owner);
        setMember(builder.member);
        setPublic_(builder.public_);
        partys.put(owner,this);
        member.forEach(key->partys.put(key,this));
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public Uni<Boolean> addMember(String name){
        if (member.stream().anyMatch(name::equalsIgnoreCase)) return Uni.createFrom().failure(new MemberExistException());
        partys.put(name,this);
        return Uni.createFrom().item(this.member.add(name));
    }

    public boolean isOwner(String name){
        return owner.equals(name);
    }

    public Uni<Boolean> disband(String sender){
        if (!isOwner(sender)) return Uni.createFrom().failure(new PartyNotOwnerException());
        this.getMember().forEach(partys::remove);
        return Uni.createFrom().item(true);
    }

    public Uni<Boolean> invite(String player){
        return this.addMember(player);
    }

    public static Uni<Boolean> publicJoin(Party party,String sender){
        if (!party.isPublic_()) return Uni.createFrom().failure(new PartyNotPublicException());
        return party.addMember(sender);
    }

    public static Uni<Party> getParty(String key){
        if (!partys.containsKey(key)) return Uni.createFrom().failure(new PartyNotFoundException());
        return Uni.createFrom().item(partys.get(key));
    }

    public static final class Builder {
        private String owner;
        private List<String> member;
        private boolean public_;

        private Builder() {
        }

        public Builder owner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder member(List<String> member) {
            this.member = member;
            return this;
        }

        public Builder public_(boolean public_) {
            this.public_ = public_;
            return this;
        }

        public Party build() {
            return new Party(this);
        }
    }
}
