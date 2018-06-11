package io.github.liuyuyu;


import java.io.OutputStream;

/**
 * @author liuyuyu
 */
public class JiaLiAo<T> {
    private Class<T> clazz;

    public JiaLiAo(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <T> JiaLiAo r(Class<T> clazz){
        return new JiaLiAo<T>(clazz);
    }

    public E e(){
        return new E();
    }

    public W<T> w(){
        return new W<T>(this.clazz);
    }

    /**
     * E技能，击飞（写）
     */
    static class E{
        public void q(OutputStream os){
        }
    }

    /**
     * W技能，吸收伤害并嘲讽敌人（读）
     */
    static class W<T>{
        private Class<T> clazz;

        public W(Class<T> clazz) {
            this.clazz = clazz;
        }

        public T q(){
            return null;
        }
    }
}
