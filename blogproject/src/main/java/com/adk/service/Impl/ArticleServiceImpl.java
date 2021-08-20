package com.adk.service.Impl;

import com.adk.dao.dos.Archives;
import com.adk.dao.mapper.ArticleBodyMapper;
import com.adk.dao.mapper.ArticleMapper;
import com.adk.dao.mapper.ArticleTagMapper;
import com.adk.dao.mapper.CategoryMapper;
import com.adk.pojo.Article;
import com.adk.pojo.ArticleBody;
import com.adk.pojo.ArticleTag;
import com.adk.pojo.SysUser;
import com.adk.service.*;
import com.adk.utils.UserThreadLocal;
import com.adk.vo.*;
import com.adk.vo.params.ArticleParams;
import com.adk.vo.params.PageParams;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result listArticle(PageParams pageParams) {
        /**
         * mybatis plus 集成了常用的sql查询语句，可以直接调用其方法进行查询
         * selectPage需要有两个条件，第一个是page对象，第二个则是查询条件
         * 这里使用Page来对pageparams进行再次封装，然后使用lambdaQueryWrapper来写查询条件
         * 这样就可以进行一次查询
         */
        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPagesize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        //相当于order by create_time desc
        queryWrapper.orderByDesc(Article::getCreateDate,Article::getWeight);
        //需要按照是否置顶进行排序

        //这样就得到了一个page 分页查询后的对象
        //然后将其放到list里去
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();

        //将获取到的article对象转换为articleVo对象
        List<ArticleVo> articleVoList =copyList(records,true,true);

        //最后封装到Result中，返回Result
        return Result.success(articleVoList);
    }

    /**
     * 该方法用于获取浏览量最高的几篇文章 数量定义在controller层中
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);

        queryWrapper.select(Article::getId,Article::getTitle);
        //只取其id和标题 其他数据不需要取
        queryWrapper.last("limit "+limit);
        //加上limit限制查询数
        //这个querywrapper是用于拼接sql语句的  可以不用自行编写sql语句
        List<Article> articles=articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }


    /**
     * 该方法用于获取日期最新的几篇文章 数量定义在controller层中 逻辑基本与最热文章相同
     * @param limit
     * @return
     */
    @Override
    public Result newArticle(int limit) {

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        //只取其id和标题 其他数据不需要取
        queryWrapper.last("limit "+limit);
        //加上limit限制查询数
        //这个querywrapper是用于拼接sql语句的  可以不用自行编写sql语句
        List<Article> articles=articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }

    /**
     * 文章归档 按照时间日月来进行文章的归档
     * 通过选择createtime的年月来进行统计
     *
     * @return
     */
    @Override
    public Result listArchives() {
        //由于不能直接获取bigint的年份 因此要进行转换
        List<Archives> archivesList=articleMapper.listArchives();
        return Result.success(archivesList);
    }

    /**
     * 实现根据文章id来查询文章信息
     * 根据article中的id去查询body和标签中的id
     * @param id
     * @return
     */
    @Override
    public Result findArticleById(Long id) {
        Article article=articleMapper.selectById(id);
        ArticleVo articleVo = copy(article,true,true,true,true);

        /**
         * 使用线程池   进行阅读量的更新
         */
        threadService.updateArticleViewCount(articleMapper,article);


        return Result.success(articleVo);
    }

    /**
     * 文章发布服务
     * @param articleParams
     * @return
     */
    @Override
    public Result publish(ArticleParams articleParams) {
        SysUser sysUser = UserThreadLocal.get();

        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setSummary(articleParams.getSummary());
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setTitle(articleParams.getTitle());
        article.setCategoryId(articleParams.getCategory().getId());
        //先插入文章 获取文章的id(数据库雪花算法自动生成)
        articleMapper.insert(article);
        //将tagid和articleid 加入到  article_tag关联表当中
        List<TagVo> tags = articleParams.getTags();

        if (tags!=null){
            for (TagVo tag : tags) {
                Long id = article.getId();
                ArticleTag artileTag=new ArticleTag();
                artileTag.setTagId(tag.getId());
                artileTag.setArticleId(id);
                articleTagMapper.insert(artileTag);
            }
        }
        //body
        ArticleBody articleBody=new ArticleBody();

        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParams.getBody().getContent());
        articleBody.setContentHtml(articleParams.getBody().getContentHtml());

        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        Map<String,String> map=new HashMap<>();
        map.put("id",article.getId().toString());

        return Result.success(map);
    }

    /**
     * 做转移，
     * 将数据库得来的records转换为渲染到页面上的ArticleAo
     * @param records
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }

    /**
     * 重载copylist 用于获取全部信息
     * @param records
     * @param isTag
     * @param isAuthor
     * @param isBody
     * @param isCategory
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }

    /**
     * 传入一个article对象 转换成articleAo
     * @param article
     * @return
     */
    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){

        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        //将查到的long型的数据转换为string赋值给前端展示对象
        String id=article.getId().toString();
        articleVo.setId(id);
        //该工具类可以将不同bean相同的属性copy过去，但是类型不同是不可以copy的

        //手动将日期的long类型转换为字符串
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        //由于并不是所有的接口都需要作者名和标签名
        //因此设置两个参数 isTag和isAuthor 来判断是否设置这两个参数
        if (isTag){
            Long articleid=article.getId();
            articleVo.setTags(tagService.findTagByArticleId(articleid));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody){
            Long bodyId=article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
