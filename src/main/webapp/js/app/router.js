define(function(require) {

	"use strict";

	// require library
	var $ = require('jquery'), Backbone = require('backbone');
	
	// require modal.js
	Backbone.ModalView = require('views/common/modal');
	
	// require alert.js
	Backbone.AlertView = require('views/common/alert');

	var $body = $('body');

	// require common view
	var ShellView = require('views/shell'), shellView = new ShellView({
		el : $body
	});

	// Close the search dropdown on click anywhere in the UI
	$body.click(function() {
		$('.dropdown').removeClass("open");
	});

	$("body").on("click", "#showMeBtn", function(event) {
		event.preventDefault();
		shellView.search();
	});

	return Backbone.Router.extend({

		routes : {
			"" : "login",
			"login" : "login",
			"home" : "home",
			"employeeList" : "employeeList",
			"employees/:id" : "employeeDetails",
			"system/:page/:id" : "system"
		},
		login : function() {
			var LoginView = require('views/sysacc/login');
			var loginView = new LoginView({
				el : $body
			}).render();
		},
		home : function() {
			shellView.render();
			var $content = $('#content', shellView.el);
			var HomeView = require('views/home');
			var homeView = new HomeView({
				el : $content
			}).render();
		},
		employeeList : function() {
			shellView.render();
			var $content = $('#content', shellView.el);

			var models = require('models/employee');
			var EmployeeListView = require('views/system/employeeList');

			var employee = new models.EmployeeCollection();
			employee.fetch({
				success : function(data) {
					var listView = new EmployeeListView({
						collection : employee,
						el : $content
					}).render();
				}
			});
		},
		employeeDetails : function(id) {
			shellView.render();
			var $content = $('#content', shellView.el);

			console.log(id);
			var models = require('models/employee');
			var EmployeeView = require('views/system/employee');

			var employee = new models.Employee({
				id : id
			});

			employee.fetch({
				success : function(data) {
					var employeeView = new EmployeeView({
						model : data,
						el : $content
					}).render();
				}
			});
		},
		system : function(page, id) {
			var Model = require('models/system/' + page);
			var View = require('views/system/' + page);
			
			var model = new Model();
			var view = new View({model : model}).render();
		}

	});

});