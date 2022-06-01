package cn.leon.order.persistence.impl;

import cn.leon.order.persistence.ResultSetConverter;
import cn.leon.order.persistence.TransactionalMessageContent;
import cn.leon.order.persistence.TransactionalMessageContentDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionalMessageContentDaoImpl implements TransactionalMessageContentDao {

    private final JdbcTemplate jdbcTemplate;

    private static final ResultSetConverter<TransactionalMessageContent> CONVERTER = r -> {
        TransactionalMessageContent content = new TransactionalMessageContent();
        content.setId(r.getLong("id"));
        content.setMessageId(r.getLong("message_id"));
        content.setContent(r.getString("content"));
        return content;
    };


    private static final ResultSetExtractor<List<TransactionalMessageContent>> MULTI = r -> {
        List<TransactionalMessageContent> list = new ArrayList<>();
        while (r.next()) {
            list.add(CONVERTER.convert(r));
        }
        return list;
    };

    @Override
    public void insert(TransactionalMessageContent record) {
        jdbcTemplate.update("INSERT INTO t_transactional_message_content(message_id, content) VALUES (?,?)",
                p -> {
                    p.setLong(1, record.getMessageId());
                    p.setString(2, record.getContent());
                });
    }

    @Override
    public List<TransactionalMessageContent> queryByMessageIds(String messageIds) {
        return jdbcTemplate.query("SELECT * FROM t_transactional_message_content WHERE message_id IN " + messageIds, MULTI);
    }
}
