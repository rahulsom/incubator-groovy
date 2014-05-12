package groovy.bugs

import groovy.transform.NotYetImplemented
import groovy.transform.stc.StaticTypeCheckingTestCase

class Groovy6786Bug extends StaticTypeCheckingTestCase {
    
    @NotYetImplemented
    void testGenericAddAll() {
        assertScript '''

            public class Class1<ENTITY> {
            
                Container<ENTITY> container;
            
                void refresh() {
                    def items = findAllItems();
                    
                    container.addAll(items);
                }
            
                Collection<ENTITY> findAllItems() {
                    return null;
                }
            }
            interface Container<ENTITY> {
                void addAll(Collection<? extends ENTITY> collection);
            }
            new Class1()

        '''
    }

    @NotYetImplemented
    void testGuavaCacheBuilderLikeGenerics() {
        assertScript '''
            class Class1 {
            
                protected LoadingCache<String, Integer> cache;
            
                Class1(CacheLoader<String, Integer> cacheLoader) {
                    this.cache = CacheBuilder.newBuilder().build(cacheLoader);
                }
            }
            
            class CacheLoader<K, V> {}
            
            class LoadingCache<K, V> {}
            
            class CacheBuilder<K, V> {
                public static CacheBuilder<Object, Object> newBuilder() {
                    return new CacheBuilder<Object, Object>();
                }
            
                public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> loader) {
                    return new LoadingCache<K1, V1>();  
                }
            }
            new Class1(null)
        '''
    }

    @NotYetImplemented
    void testOverrideGenericMethod() {
        assertScript '''
            abstract class AbstractManager <T> {
                protected boolean update(T item) {
                    return false;
                }
            }
            
            abstract class AbstractExtendedManager<T> extends AbstractManager<T> {}
                        
            class ConcreteManager extends AbstractExtendedManager<String> {
                @Override
                 protected boolean update(String item){
                    return super.update(item);
                }
            }
            new ConcreteManager();
        '''
    }

    @NotYetImplemented
    void testIfWithInstanceOfAndAnotherConditionAfter() {
        assertScript '''
            class Class1 {
            
            
                Class1() {
                    Object obj = null;
                    
                    if(obj instanceof String && someCondition() && testMethod(obj)) {
                        println "Hello!"
                    }
                }
                
                boolean someCondition() {
                    return true;
                }
                
                boolean testMethod(String arg) {
                    return true;
                }
            }
            new Class1();
        '''
    }
    
}
