package com.example.demo;

import jakarta.persistence.Embeddable;

@Embeddable
public class Skill {
	public String name;
	public int level; // 0 a 100
}
