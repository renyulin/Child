package child.ryl.child.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import child.ryl.child.R;
import child.ryl.child.base.ETActivity;
import child.ryl.child.db.bean.Article;
import child.ryl.child.db.bean.Student;
import child.ryl.child.db.bean.User;
import child.ryl.child.db.helper.ArticleDao;
import child.ryl.child.db.helper.DatabaseHelper;
import child.ryl.child.db.helper.UserDao;

public class SqlActivity extends ETActivity implements View.OnClickListener, View.OnLongClickListener {
    private EditText hello_world;
    private Button main_button;
    private int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_primary);
        init();
    }

    private void init() {
        main_button = (Button) findViewById(R.id.myHello);
        hello_world = (EditText) findViewById(R.id.myEdit);
        main_button.setOnClickListener(this);
        main_button.setOnLongClickListener(this);
    }

    public void testAddArticle() {
        User u = new User();
        u.setName("张鸿洋");
        new UserDao(this).add(u);
        for (int i = 0; i < 2; i++) {
            Article article = new Article();
            article.setTitle("ORMLite的使用" + i);
            article.setUser(u);
            new ArticleDao(this).add(article);
        }
    }

    public void testGetArticleById() {
        Article article = new ArticleDao(this).get(i);
        e(article.getUser().toString() + " , " + article.getTitle());
    }

    public void testGetArticleWithUser() {
        Article article = new ArticleDao(this).getArticleWithUser(i);
        e(article.getUser() + " , " + article.getTitle());
    }

    public void testListArticlesByUserId() {
        List<Article> articles = new ArticleDao(this).listByUserId(i);
        e(articles.toString());
    }

    public void testGetUserById() {
        User user = new UserDao(this).get(i);
        e(user.getName());
        if (user.getArticles() != null)
            for (Article article : user.getArticles()) {
                e(article.toString());
            }
    }

    public void testAddStudent() throws SQLException {
        Dao dao = DatabaseHelper.getHelper(this).getDao(Student.class);
        Student student = new Student();
        student.setDao(dao);
        student.setName("张鸿洋");
        student.create();
    }

    @Override
    public void onClick(View view) {
        String code = hello_world.getText().toString();
        if (TextUtils.isEmpty(code)) {
            code = "0";
        }
        if (code.equals("1")) {
            testAddArticle();
        } else if (code.equals("2")) {
            try {
                testAddStudent();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (code.equals("3")) {
            testGetArticleById();
        } else if (code.equals("4")) {
            testGetArticleWithUser();
        } else if (code.equals("5")) {
            testListArticlesByUserId();
        } else if (code.equals("6")) {
            testGetUserById();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        i++;
        e("i=" + i);
        return true;
    }
}
