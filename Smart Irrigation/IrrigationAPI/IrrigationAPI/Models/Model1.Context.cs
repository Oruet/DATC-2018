﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace IrrigationAPI.Models
{
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Infrastructure;
    
    public partial class IrigationDBEntities : DbContext
    {
        public IrigationDBEntities()
            : base("name=IrigationDBEntities")
        {
        }
    
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            throw new UnintentionalCodeFirstException();
        }
    
        public virtual DbSet<Senzori> Senzoris { get; set; }
        public virtual DbSet<Istoric> Istorics { get; set; }
        public virtual DbSet<Value> Values { get; set; }
    }
}
