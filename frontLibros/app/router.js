import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.route('book');
  this.route('createBook');
  this.route('sesion');
  this.route('client');
});


export default Router;
