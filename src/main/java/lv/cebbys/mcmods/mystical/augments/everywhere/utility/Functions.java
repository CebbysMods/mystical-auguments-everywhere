package lv.cebbys.mcmods.mystical.augments.everywhere.utility;

public interface Functions {

    interface P1<P1, O> {
        O apply(P1 p1);
    }

    interface P2<P1, P2, O> {
        O apply(P1 p1, P2 p2);
    }

    interface P3<P1, P2, P3, O> {
        O apply(P1 p1, P2 p2, P3 p3);
    }

    interface P4<P1, P2, P3, P4, O> {
        O apply(P1 p1, P2 p2, P3 p3, P4 p4);
    }

    interface P5<P1, P2, P3, P4, P5, O> {
        O apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5);
    }
}
