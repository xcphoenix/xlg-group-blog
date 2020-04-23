package top.xcphoenix.groupblog.mybatis.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import top.xcphoenix.groupblog.model.dao.Auth;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * User.Auth 类型转换
 *
 * @author      xuanc
 * @date        2020/4/23 下午6:22
 * @version     1.0
 */ 
public class AuthHandler implements TypeHandler<Auth> {

    private final Map<Integer, Auth> authMap = new HashMap<>();

    public AuthHandler() {
        for (Auth auth : Auth.values()) {
            authMap.put(auth.getVal(), auth);
        }
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Auth parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getVal());
    }

    @Override
    public Auth getResult(ResultSet rs, String columnName) throws SQLException {
        Integer val = rs.getInt(columnName);
        return authMap.get(val);
    }

    @Override
    public Auth getResult(ResultSet rs, int columnIndex) throws SQLException {
        Integer val = rs.getInt(columnIndex);
        return authMap.get(val);
    }

    @Override
    public Auth getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Integer val = cs.getInt(columnIndex);
        return authMap.get(val);
    }

}
