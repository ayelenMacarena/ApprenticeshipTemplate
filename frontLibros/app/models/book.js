import DS from 'ember-data';

export default DS.Model.extend({

  nombreLibro: DS.attr('String'),
  isbn: DS.attr('String'),
  precio: DS.attr('Number')

});
