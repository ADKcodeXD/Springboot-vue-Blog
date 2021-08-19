package com.adk.service.Impl;

import com.adk.dao.mapper.CommentsMapper;
import com.adk.pojo.Comment;
import com.adk.service.CommentsService;
import com.adk.service.SysUserService;
import com.adk.vo.CommentVo;
import com.adk.vo.Result;
import com.adk.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据文章id查询评论列表 并将表中的id的一对多的id查询各种其他表中的信息封装到CommentsVo对象中去
     * @param id
     * @return
     */
    @Override
    public Result commentsByArticleId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> commentList = commentsMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList=copyList(commentList);

        return Result.success(commentVoList);
    }






    /**
     * 以下的都是工具类使用  用于复制对象  不处理业务逻辑
     * @param commentList
     * @return
     */
    private List<CommentVo> copyList(List<Comment> commentList) {
        List<CommentVo> commentVoList=new ArrayList<>();
        for (Comment comment : commentList) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo=new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        //id 转换
        String id = comment.getId().toString();
        commentVo.setId(id);
        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = comment.getLevel();
        if (level==1){
            Long id2 =comment.getId();
            List<CommentVo> commentVoList=findCommentsByParentId(id2);
            commentVo.setChildrens(commentVoList);
        }
        if(level>1){
            Long toUid = comment.getToUid();
            UserVo toUser = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUser);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id2) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getId,id2);
        queryWrapper.eq(Comment::getLevel,2);
        return copyList(commentsMapper.selectList(queryWrapper));
    }
}
