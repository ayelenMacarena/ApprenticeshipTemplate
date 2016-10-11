import Ember from 'ember';

export default Ember.Route.extend({
  model: function() {
    return [{id: 1,isbn:'987654576', titulo: "Harry Potter 1", precio: "$123"},
            {id: 2, isbn:'98761234', titulo: "Harry Potter 2", precio:"$50"},
            {id: 3, isbn:'98769876', titulo: "Harry Potter 3", precio:"$100"}
      ];
  }
});
