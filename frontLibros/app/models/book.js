import DS from 'ember-data';

export default DS.Model.extend({
    isbn: DS.attr('String'),
    titulo: DS.attr('String'),
    precio: DS.attr('String')

});
