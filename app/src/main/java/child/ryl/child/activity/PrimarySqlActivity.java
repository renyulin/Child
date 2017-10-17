package child.ryl.child.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

import child.ryl.child.R;
import child.ryl.child.base.ETActivity;
import child.ryl.child.db.bean.UserTest;
import child.ryl.child.db.helper.DatabaseHelperPrimary;

/**
 * http://blog.csdn.net/lmj623565791/article/details/39122981
 */
public class PrimarySqlActivity extends ETActivity implements View.OnClickListener {
    private Button myHello;
    private EditText myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_primary);
        initView();
    }

    private void initView() {
        myHello = (Button) findViewById(R.id.myHello);
        myHello.setOnClickListener(this);
        myEdit = (EditText) findViewById(R.id.myEdit);
    }

    @Override
    public void onClick(View view) {
        String edit = myEdit.getText().toString();
        if (TextUtils.isEmpty(edit)) {
            return;
        }
        if ("0".equals(edit)) {
            addUserTest();
            toast("addUserTest");
        } else if ("1".equals(edit)) {
            queryAll();
            toast("queryAll");
        } else if ("2".equals(edit)) {
            deleteUserTest();
            toast("deleteUserTest");
        } else if ("3".equals(edit)) {
            updateUserTest();
            toast("updateUserTest");
        } else if ("4".equals(edit)) {
            queryUserTest();
            toast("queryUserTest");
        } else if ("5".equals(edit)) {
            startActivity(new Intent(this, SqlActivity.class));
        }
    }

    /**
     * queryAll
     */
    private void queryAll() {
        DatabaseHelperPrimary helper = DatabaseHelperPrimary.getHelper(this);
        try {
            //查找所有
//            List<UserTest> UserTests = helper.getUserDao().queryForAll();
            //通过条件查询所有
//            List<UserTest> userTests = helper.getUserDao().queryBuilder().
//                    where().eq("name", "zhy6").query();


//            QueryBuilder<UserTest, Integer> queryBuilder = helper.getUserDao()
//                    .queryBuilder();


            Where<UserTest, Integer> where = helper.getUserDao().queryBuilder().where();
            where.eq("name", "zhy");
            where.and();
            where.eq("descd", "2B青年");
            where.and();
            where.eq("id", 13);
            List<UserTest> userTests = where.query();
//            List<UserTest> us= where.query();
/*上述两种都相当与：select * from tb_article where user_id = 1 and name = 'xxx' ; */
            //或者
            /*Where<UserTest, Integer> where =*/
//            helper.getUserDao().queryBuilder().//
//                    where().//
//                    eq("user_id", 1).and().//
//                    eq("name", "xxx");
//
//            /*select * from tb_article where ( user_id = 1 and name = 'xxx' )
//             or ( user_id = 2 and name = 'yyy' )  ;
//            好了，再复杂的查询估计也能够凑出来了~~*/
//            where.or(
//                    //
//                    where.and(//
//                            where.eq("user_id", 1), where.eq("name", "xxx")),
//                    where.and(//
//                            where.eq("user_id", 2), where.eq("name", "yyy")));
            if (userTests == null) {
                return;
            }
            e(userTests.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * addUserTest
     */
    private void addUserTest() {
        UserTest u1 = new UserTest("zhy", "2B青年");
        DatabaseHelperPrimary helper = DatabaseHelperPrimary.getHelper(this);
        try {
            helper.getUserDao().create(u1);
            u1 = new UserTest("zhy2", "2B青年");
            helper.getUserDao().create(u1);
            u1 = new UserTest("zhy3", "2B青年");
            helper.getUserDao().create(u1);
            u1 = new UserTest("zhy4", "2B青年");
            helper.getUserDao().create(u1);
            u1 = new UserTest("zhy5", "2B青年");
            helper.getUserDao().create(u1);
            u1 = new UserTest("zhy6", "2B青年");
            helper.getUserDao().create(u1);
        } catch (SQLException e) {
            e(e.toString());
        }
    }

    /**
     * updateUserTest
     */
    private void updateUserTest() {
        DatabaseHelperPrimary helper = DatabaseHelperPrimary.getHelper(this);
        try {
            UserTest u = new UserTest("zhyyy", "青年");
            u.setId(3);
            helper.getUserDao().update(u);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * deleteUserTest
     */
    private void deleteUserTest() {
        DatabaseHelperPrimary helper = DatabaseHelperPrimary.getHelper(this);
        try {
            //删除单个
            helper.getUserDao().deleteById(2);
            //根据用户删除
//            UserTest u=helper.getUserDao().queryForId(6);
//            helper.getUserDao().delete(u);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * queryUserTest
     */
    private void queryUserTest() {
        DatabaseHelperPrimary helper = DatabaseHelperPrimary.getHelper(this);
        try {
            UserTest u = helper.getUserDao().queryForId(2);
            if (u == null) {
                return;
            }
            e(u.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
/*使用queryBuilder是因为我们希望执行完成查询直接返回List<Bean>集合；
对于Update我们并不关注返回值，直接使用
articleDaoOpe.updateRaw(statement, arguments);传入sql和参数即可~~
何必在那articleDaoOpe.updateBuilder().updateColumnValue("name","zzz").where().eq("user_id", 1);这样的痛苦呢~~~
同理还有deleteBuilder还是建议直接拼写sql，当然很简单的除外，直接使用它的API~*/