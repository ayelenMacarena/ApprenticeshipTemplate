import Ember from 'ember';

export default Ember.Controller.extend({
  actions:{
    crearLibro () {
      this.get('model').save().then(() => {
        this.transitionToRoute('book');
      });
    }
  }
});
