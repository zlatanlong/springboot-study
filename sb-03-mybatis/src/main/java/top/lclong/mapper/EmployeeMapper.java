package top.lclong.mapper;

import top.lclong.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmployeeMapper {

    // 获取所有员工信息
    public abstract List<Employee> getEmployees();

    // 新增一个员工
    public abstract int save(Employee employee);

    // 通过id获得员工信息
    public abstract Employee get(Integer id);

    // 通过id删除员工
    public abstract int delete(Integer id);

}
