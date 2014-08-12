define(function (require) {

    "use strict";

    var $                   = require('jquery'),
        _                   = require('underscore'),
        Backbone            = require('backbone'),
        EmployeeListView    = require('views/system/employeeList'),
        tpl                 = require('text!tpl/system/Employee.html'),

        template = _.template(tpl);

    return Backbone.View.extend({

        render: function () {
            this.$el.html(template(this.model.attributes));
            this.model.reports.fetch({
                success: function (data) {
                    if (data.length === 0) {
                        $('.no-reports').show();
                    }
                }
            });
            var listView = new EmployeeListView({collection: this.model.reports, el: $('.report-list', this.el)});
            listView.render();
            return this;
        }
    });

});