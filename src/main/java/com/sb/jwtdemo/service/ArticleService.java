package com.sb.jwtdemo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.jwtdemo.entity.Article;
import com.sb.jwtdemo.entity.ArticleRepository;

@Service
public class ArticleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleService.class);
	
	@Autowired
	private ArticleRepository repository;
	
	public Article getArticleById(long articleId) {
		return repository.findById(articleId).get();
	}
	
	public List<Article> getAllArticles(){
		return repository.findAll();
	}
}
