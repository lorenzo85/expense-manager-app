package org.cms.core.visitor;


public interface Visitable {

    void accept(Visitor visitor);

}
