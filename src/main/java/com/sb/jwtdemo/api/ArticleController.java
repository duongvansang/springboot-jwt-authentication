package com.sb.jwtdemo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.jwtdemo.entity.Article;
import com.sb.jwtdemo.service.ArticleService;

@RestController
@RequestMapping("/v1/article")
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getArticleById(@PathVariable("id") Integer id) {
		Article article = articleService.getArticleById(id);
		APITemplate res = new APITemplate(HttpStatus.OK, "Operation succeeded");
		res.setData(article);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllArticles() {
		List<Article> list = articleService.getAllArticles();
		APITemplate res = new APITemplate(HttpStatus.OK, "Operation succeeded");
		res.setData(list);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}  
