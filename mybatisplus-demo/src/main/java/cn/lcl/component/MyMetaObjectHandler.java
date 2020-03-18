package cn.lcl.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {

        /**
         * 如果有的实体有createTime字段需要fill，
         * 有的实体没有，并且添加createTime字段
         * 还需要额外开销，
         * 可以先进行判断
         */
        boolean hasSetter = metaObject.hasSetter("createTime");

        /**
         * 先看是否主动设了值
         */
        Object createTime = getFieldValByName("createTime", metaObject);

        if (hasSetter && createTime == null) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
