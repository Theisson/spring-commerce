import { useState } from 'react'
import { Search, ShoppingBag, Menu, X, Leaf } from 'lucide-react'

const navLinks = [
  { label: 'Início', href: '#' },
  { label: 'Catálogo', href: '#catalog' },
  { label: 'Categorias', href: '#' },
  { label: 'Sobre', href: '#' },
]

export function Navbar() {
  const [mobileOpen, setMobileOpen] = useState(false)

  return (
    <header className="sticky top-0 z-50 bg-[#F5F3EE]/95 backdrop-blur-sm border-b border-[#D6DDD0]">
      <nav className="max-w-screen-xl mx-auto px-6 lg:px-16 h-[72px] flex items-center justify-between gap-8">

        {/* Logo */}
        <a href="#" className="flex items-center gap-2 shrink-0">
          <Leaf size={24} className="text-[#2D5E3A]" strokeWidth={2.5} />
          <span className="text-[#1B3A28] text-lg font-bold tracking-tight">
            SpringCommerce
          </span>
        </a>

        {/* Links — desktop */}
        <ul className="hidden md:flex items-center gap-8">
          {navLinks.map((link) => (
            <li key={link.label}>
              <a
                href={link.href}
                className="text-sm font-medium text-[#7A9A80] hover:text-[#1B3A28] transition-colors duration-200 first:text-[#2D5E3A]"
              >
                {link.label}
              </a>
            </li>
          ))}
        </ul>

        {/* Ações — desktop */}
        <div className="hidden md:flex items-center gap-3">
          <button className="w-9 h-9 flex items-center justify-center rounded-xl bg-[#EBE9E3] hover:bg-[#D6DDD0] text-[#7A9A80] hover:text-[#1B3A28] transition-colors duration-200" aria-label="Buscar">
            <Search size={17} />
          </button>
          <button className="relative w-9 h-9 flex items-center justify-center rounded-xl bg-[#EBE9E3] hover:bg-[#D6DDD0] text-[#1B3A28] transition-colors duration-200" aria-label="Carrinho">
            <ShoppingBag size={17} />
            <span className="absolute -top-1 -right-1 w-4 h-4 bg-[#2D5E3A] text-white text-[9px] font-bold rounded-full flex items-center justify-center">
              0
            </span>
          </button>
          <button className="bg-[#2D5E3A] hover:bg-[#1B3A28] text-white text-sm font-semibold px-5 h-9 rounded-lg transition-colors duration-200">
            Entrar
          </button>
        </div>

        {/* Botão hamburger — mobile */}
        <button
          className="md:hidden w-9 h-9 flex items-center justify-center text-[#1B3A28]"
          onClick={() => setMobileOpen(!mobileOpen)}
          aria-label="Menu"
        >
          {mobileOpen ? <X size={22} /> : <Menu size={22} />}
        </button>
      </nav>

      {/* Menu mobile */}
      {mobileOpen && (
        <div className="md:hidden border-t border-[#D6DDD0] bg-[#F5F3EE] px-6 py-4 flex flex-col gap-4">
          {navLinks.map((link) => (
            <a
              key={link.label}
              href={link.href}
              className="text-sm font-medium text-[#1B3A28] py-1"
              onClick={() => setMobileOpen(false)}
            >
              {link.label}
            </a>
          ))}
          <hr className="border-[#D6DDD0]" />
          <button className="w-full bg-[#2D5E3A] text-white text-sm font-semibold py-2.5 rounded-lg">
            Entrar
          </button>
        </div>
      )}
    </header>
  )
}
