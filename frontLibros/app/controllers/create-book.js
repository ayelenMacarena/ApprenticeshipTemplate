import Ember from 'ember';

export default Ember.Controller.extend({
  actions:{
    crearLibro () {
      this.get('model').save();
      //this.get('model')

    }
  }
});
