import Ember from 'ember';

export default Ember.Controller.extend({
  actions:{
    login () {

      this.get('model').save();    }
  }
});
