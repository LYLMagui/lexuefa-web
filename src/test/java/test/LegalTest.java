package test;

import com.lexuefa.LeXueFaApplication;
import com.lexuefa.dao.LegalDao;
import com.lexuefa.entity.LegalEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author ukir
 * @date 2023/04/19 17:24
 **/
@SpringBootTest(classes = LeXueFaApplication.class)
public class LegalTest {
    
    @Autowired
    private LegalDao legalDao;
    @Test
    public void testList(){
        List<LegalEntity> legalEntities = legalDao.selectList(null);
        System.out.println(legalEntities);
    }
}
