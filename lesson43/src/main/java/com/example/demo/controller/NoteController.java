package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Note;

@Controller
@RequestMapping("/notes")
public class NoteController {
	private List<Note> notes = new ArrayList<>();

	@GetMapping("/all")
	public String notes(Model model) {
		model.addAttribute("notes", notes);
		return "notes";
	}

	@GetMapping("/create")
	public String create() {
		return "note_form";
	}
	
	@PostMapping("/add")
	public String add(@ModelAttribute("note") Note note) {
		note.setId(notes.stream()
				.max((u1, u2)-> u1.getId()-u2.getId())
				.orElseGet(()->new Note())
				.getId()+1);
		notes.add(note);
		return "redirect:/notes/all";
	}
}
